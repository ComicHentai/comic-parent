###ComicHentai-Service

RPC Service Provider/Client and RESTFul API Of ComicHentai

### Sub Modules

####comic-dependencies  
	
	manage dependencies of whole project
	
####comic-rest-api  

	provide fast/stable RESTFul API. Base on Spring-Boot

####comic-service-impl  
	
	implement java service api

####comic-web  

	web module (if need)

####comic-deploy

    packaging/deploy rpc service module
    
####comic-service-monitor

    the monitor of rpc service (such as qps...)
 
####comic-share  

	common class module , such as classes,tools,entry,domain...etc
	
#####comic-dal
	
	base database data object operation , such as crud elements.

#####comic-service
	
	data transform object and base service overriding daos' methods and logic operation.but not explose the service.
	In Business Object .Services has assembly as a component.return by ResultSupport Class.
	Then explose to the rpc or restful client

#####comic-tools
	
	Commons tools such like serialize,encoding ,page,exception,object convert...