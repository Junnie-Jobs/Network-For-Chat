'use strict';

angular.module('networkChat.messages', [])
	.directive('printMessage', function () {
	    return {
	        template: '<strong>{{message.username}}<span ng-show="message.to"> -> {{message.to}}</span>:</strong> {{message.message}}<br/>'
	       
	    };
});