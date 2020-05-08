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

var _self = {},
	_ID = "cordova-plugin-bb-crypto",
	exec = cordova.require("cordova/exec");

	// These methods are called by your App's JavaScript
	// They make WebWorks function calls to the methods
	// in the index.js of the Extension

	/**
	 * Cryptographic Hash Function
	*/
	_self.hash = function (input) {
		var result,
			success = function (data, response) {
				result = JSON.parse(data);
			},
			fail = function (data, response) {
				console.log("Error: " + data);
			};
		exec(success, fail, _ID, "hash", { input: input });
		return result;
	};
	
	/**
	 * Pseudo Random Data Generator
	*/
	_self.random = function (input) {
		var result,
			success = function (data, response) {
				result = JSON.parse(data);
			},
			fail = function (data, response) {
				console.log("Error: " + data);
			};
		exec(success, fail, _ID, "random", { input: input });
		return result;
	};
	
	/**
	 * AES-CBC Encryption
	*/
	_self.encrypt = function (input) {
		var result,
			success = function (data, response) {
				result = JSON.parse(data);
			},
			fail = function (data, response) {
				console.log("Error: " + data);
			};
		exec(success, fail, _ID, "encrypt", { input: input });
		return result;
	};
	
	/**
	 * AES-CBC Decryption
	*/
	_self.decrypt = function (input) {
		var result,
			success = function (data, response) {
				result = JSON.parse(data);
			},
			fail = function (data, response) {
				console.log("Error: " + data);
			};
		exec(success, fail, _ID, "decrypt", { input: input });
		return result;
	};


module.exports = _self;
