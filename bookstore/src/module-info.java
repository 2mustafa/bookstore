module cookbook_model {
	requires transitive java.logging;
	requires transitive javax.annotation.api;
	requires transitive java.instrument;
	requires transitive java.activation;
	
	requires transitive java.validation;
	requires transitive java.json.bind;

	requires transitive javax.persistence;
	requires transitive eclipselink.minus.jpa;
	requires transitive java.sql;
	
	
	requires transitive jdk.httpserver;
	requires transitive java.ws.rs;
	requires transitive jersey.server;
	requires transitive jersey.container.jdk.http;
	
	
	
	exports persistence;
	exports service;
	exports tool;

	opens persistence;
	opens service;
	opens server;
}