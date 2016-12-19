'use strict';

angular.module('springChat.controllers', ['toaster'])
	.controller('ChatController', ['$scope', '$location', '$interval', 'toaster', 'ChatSocket', function($scope, $location, $interval, toaster, chatSocket) {
		  
		var typing = undefined;
		
		$scope.username     = '';
		$scope.sendTo       = 'everyone';
		$scope.participants = [];
		$scope.messages     = [];
		$scope.newMessage   = ''; 
		  
		$scope.sendMessage = function() {
			var destination = "/app/chat.message";	
			chatSocket.send(destination, {}, JSON.stringify({message: $scope.newMessage}));
			$scope.newMessage = '';
		};
		
		$scope.startTyping = function() {		
	        if (angular.isDefined(typing)) return;
	        
	        typing = $interval(function() {
	                $scope.stopTyping();
	            }, 500);
	        
	        chatSocket.send("/topic/chat.typing", {}, JSON.stringify({username: $scope.username, typing: true}));
		};
		
		$scope.stopTyping = function() {
			if (angular.isDefined(typing)) {
		        $interval.cancel(typing);
		        typing = undefined;
		        
		        chatSocket.send("/topic/chat.typing", {}, JSON.stringify({username: $scope.username, typing: false}));
			}
		};
			
		var initStompClient = function() {
			chatSocket.init('/ws');
			
			chatSocket.connect(function(frame) {
				  
				$scope.username = frame.headers['user-name'];

				chatSocket.subscribe("/app/chat.participants", function(message) {
					$scope.participants = JSON.parse(message.body);
				});
				  
				chatSocket.subscribe("/topic/chat.login", function(message) {
					$scope.participants.unshift({username: JSON.parse(message.body).username, typing : false});
				});
		        	 
				chatSocket.subscribe("/topic/chat.logout", function(message) {
					var username = JSON.parse(message.body).username;
					for(var index in $scope.participants) {
						if($scope.participants[index].username == username) {
							$scope.participants.splice(index, 1);
						}
					}
		        });
		        	 
				chatSocket.subscribe("/topic/chat.typing", function(message) {
					var parsed = JSON.parse(message.body);
					if(parsed.username == $scope.username) return;
				  					
					for(var index in $scope.participants) {
						var participant = $scope.participants[index];
						  
						if(participant.username == parsed.username) {
							$scope.participants[index].typing = parsed.typing;
						}
				  	} 
				});
		        	 
				chatSocket.subscribe("/topic/chat.message", function(message) {
					$scope.messages.unshift(JSON.parse(message.body));
		        });
				  
				chatSocket.subscribe("/user/exchange/amq.direct/chat.message", function(message) {
					var parsed = JSON.parse(message.body);
					parsed.priv = true;
					$scope.messages.unshift(parsed);
		        });
				  
				chatSocket.subscribe("/user/exchange/amq.direct/errors", function(message) {
					toaster.pop('error', "Error", message.body);
		        });
		          
			}, function(error) {
				toaster.pop('error', 'Error', 'Connection error ' + error);
				
		    });
		};
		  
		initStompClient();
	}]);
	