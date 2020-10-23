Laboratorio de WebServices

**Elementos a utilizar**
* JavaEE, JDK 1.8 o superior
* IDE Eclipse, elegir la opción "Eclipse for Java EE developers"
* Instalar PostMan https://www.postman.com/
* Instalar el motor de base de datos Postgresql

**Descargar el proyecto**

Via SSH:
    
    git clone git@gitlab.com:fmancia/sd.git
    
Via HTTPS:
    
    git clone https://gitlab.com/fmancia/sd.git
    
Via transferencia de archivos por parte del Profesor

    
**Importar proyecto**
 * Abrir el IDE Eclipse
 * Menú File -> Import -> Existing Maven Project
 * Dentro de los archivos descargados, especificar la carpeta "sd\lab-ws\servidor\personas"
 * Finalizar
 * El IDE comenzará a importar las librerías de Maven
 
**Base de datos**
 * Crear una base de datos.
 * Configurar los datos de configuración y acceso en la clase "Bd.java".

```sql
CREATE TABLE persona
(
    cedula integer NOT NULL,
    nombre character varying(1000),
    apellido character varying(1000),
    CONSTRAINT pk_cedula PRIMARY KEY (cedula)
)
WITH (
   OIDS=FALSE
 );
```

**Verificación del código fuente**
 * Verificación de la clase JaxRsActivator.java
 * Verificación de la clase Bd.java
 * Verificación de la clase Persona.java
 * Verificación de la clase PersonaDAO.java
 * Verificación de la clase PersonaService.java
 * Verificación de la clase PersonaRESTService.java

**Deployar en Servidor**
 * Desde el IDE Eclipse, configurar el servidor de aplicaciones Wildfly10 (Verificar la guía de clase anterior sobre el laboratorio de servidor de aplicaciones JavaEE)
 * Deploy del proyecto "personas" en el servidor Wildfly


**Verificación con herramientas: POSTMAN / SOAP-UI / Browser**
 * GET http://localhost:8080/personas/rest/personas
 * GET http://localhost:8080/personas/rest/personas/3298639
 * GET http://localhost:8080/personas/rest/personas/cedula?cedula=160160
 * POST http://localhost:8080/personas/rest/personas/
 * DELETE http://localhost:8080/personas/rest/personas/150150
 * Imágenes de ejemplo de utilización de POSTMAN para la petición POST en el directorio: https://gitlab.com/fmancia/sd/tree/master/lab-ws/cliente-postman
 

	 
	 
**Ejemplo de llamado a servicio REST utilizando métodos de Java Estandar**

        try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("GET");
			
			if (conn.getResponseCode() < 200  || conn.getResponseCode() >= 300 ) {
				throw new RuntimeException("Error HTTP - código : " + conn.getResponseCode()+" : "+conn.getResponseMessage());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Impresión del contenido de la respuesta: \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}