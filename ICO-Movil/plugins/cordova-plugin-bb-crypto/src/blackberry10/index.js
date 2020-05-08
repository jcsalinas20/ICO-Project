/*
* Copyright (c) 2013-2014 BlackBerry Limited
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

var gseCrypto,
	resultObjs = {},
   _utils = require("../../lib/utils");

module.exports = {

	// Code can be declared and used outside the module.exports object,
	// but any functions to be called by client.js need to be declared
	// here in this object.

	// These methods call into JNEXT.GSECrypto which handles the
	// communication through the JNEXT plugin to gseCrypto_js.cpp

	hash: function (success, fail, args, env) {
		var result = new PluginResult(args, env);
		args = JSON.parse(decodeURIComponent(args["input"]));
		result.ok(gseCrypto.getInstance().hash(result.callbackId, args), false);
	},
	
	random: function (success, fail, args, env) {
		var result = new PluginResult(args, env);
		args = JSON.parse(decodeURIComponent(args["input"]));
		result.ok(gseCrypto.getInstance().random(result.callbackId, args), false);
	},
	
	encrypt: function (success, fail, args, env) {
		var result = new PluginResult(args, env);
		args = JSON.parse(decodeURIComponent(args["input"]));
		result.ok(gseCrypto.getInstance().encrypt(result.callbackId, args), false);
	},
	
	decrypt: function (success, fail, args, env) {
		var result = new PluginResult(args, env);
		args = JSON.parse(decodeURIComponent(args["input"]));
		result.ok(gseCrypto.getInstance().decrypt(result.callbackId, args), false);
	}

};

///////////////////////////////////////////////////////////////////
// JavaScript wrapper for JNEXT plugin for connection
///////////////////////////////////////////////////////////////////

JNEXT.GSECrypto = function () {
	var self = this,
		hasInstance = false;

	self.getId = function () {
		return self.m_id;
	};

	self.init = function () {
		if (!JNEXT.require("libGSECrypto")) {
			return false;
		}

		self.m_id = JNEXT.createObject("libGSECrypto.GSECryptoJS");

		if (self.m_id === "") {
			return false;
		}

		JNEXT.registerEvents(self);
	};

	// ************************
	// Enter your methods here
	// ************************

	// calls into InvokeMethod(string command) in gseCrypto_js.cpp

	self.hash = function (callbackId, input) {
		return JNEXT.invoke(self.m_id, "hash " + callbackId + " " + JSON.stringify(input) );
	};

	self.random = function (callbackId, input) {
		return JNEXT.invoke(self.m_id, "random " + callbackId + " " + JSON.stringify(input) );
	};
	
	self.encrypt = function (callbackId, input) {
		return JNEXT.invoke(self.m_id, "encrypt " + callbackId + " " + JSON.stringify(input) );
	};
	
	self.decrypt = function (callbackId, input) {
		return JNEXT.invoke(self.m_id, "decrypt " + callbackId + " " + JSON.stringify(input) );
	};

	// ************************
	// End of methods to edit
	// ************************
	self.m_id = "";

	self.getInstance = function () {
		if (!hasInstance) {
			hasInstance = true;
			self.init();
		}
		return self;
	};

};

gseCrypto = new JNEXT.GSECrypto();
