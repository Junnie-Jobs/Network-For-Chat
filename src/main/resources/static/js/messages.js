'use strict';

angular.module('springChat.messages', [])
	.directive('printMessage', function () {
	    return {
	        template: '<span ng-show="message.priv">[private] </span><strong>{{message.username}}<span ng-show="message.to"> -> {{message.to}}</span>:</strong> {{message.message}}<br/>'
	       
	    };
});