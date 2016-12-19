'use strict';

angular.module('springChat.directives', [])
	.directive('printMessage', function () {
	    return {
	        template: '<span ng-show="message.priv">[private] </span><strong>{{message.username}}<span ng-show="message.to"> -> {{message.to}}</span>:</strong> {{message.message}}<br/>'
	       
	    };
});