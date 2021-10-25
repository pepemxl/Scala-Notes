# ABI

## Interfaz binaria de aplicaciones

En desarollo de software una interfaz binaria de aplicación (ABI) es la interfaz entre dos módulos, uno de los cuales es usualmente una librería o un sistema operativo, a nivel de lenguaje de máquina. 

Una ABI determina detalles como la forma de llamar a las funciones, en qué formato binario se debería pasar la información de un componente de programa al siguiente, o al sistema operativo en el caso de una llamada al sistema.

Las ABIs se distinguen de las interfaces de programación de aplicaciones (API) en que ambas definen interfaces entre componentes de programa pero las API a nivel de código fuente.


Las ABIs cubren aspectos como:

- tamaños, disposición y alineamiento de los tipos de datos
- la convención de llamada, que controla cómo se pasan los argumentos de las funciones y se recuperan los valores devueltos; por ejemplo, si todos los parámetros se pasan a la pila o si algunos parámetros pasan a los registros, qué registros se utilizan para qué parámetros de una función, y qué parámetro pasa primero a la pila, si pasa el primero o el último
- cómo una aplicación debería realizar llamadas al sistema del sistema operativo y, si la ABI especifica llamadas directas al sistema en vez de llamadas de procedimiento, las direcciones de llamada
- y en el caso de un ABI de sistema operativo completo, el formato binario de los archivos objeto de las librerías de programa, etc.

Un ABI completo, como el Estándar de Compatibilidad Binaria de Intel (iBCS),​ permite a un programa de un sistema operativo soportar dicho ABI para ejecutarse sin modificaciones en cualquier otro sistema al que se le provean de las librerías compartidas necesarias y tenga los mismos prerrequisitos.

Otras ABIs estandarizan detalles como la convención de nombres de funciones en C++,​ manejo de excepciones, propagación,​ y convención sobre llamadas entre compiladores de la misma plataforma que no requieren compatibilidad con otras plataformas.



## EABI

Una interfaz binaria de aplicación embebida (EABI, embedded-application binary interface) especifica convenciones estandarizadas para los formatos de archivo, tipos de datos, uso de registros, organización de la pila y paso de parámetros en funciones de una aplicación de un sistema embebido.

Los compiladores que soportan EABI crean código objeto compatible con el código generado por otros compiladores, permitiendo a los desarrolladores enlazar librerías generadas con otros compiladores. Los desarrolladores que escriben su propio código en lenguaje ensamblador pueden usar EABI para interactuar con el ensamblador generado por otro compilador.

Las diferencias principales entre EABI y ABI para sistemas operativos de propósito general son que se permiten instrucciones privilegiadas en el código de la aplicación sin necesidad de enlazado dinámico y se utiliza un marco de pila más compacto para ahorrar memoria. La elección de EABi puede afectar al rendimiento.

Ejemplos de EABIs ampliamente utilizadas: PowerPC, ARM EABI2 y MIPS EABI.9


## Interfaz de funciones foráneas

Una **interfaz de funciones foráneas** (Foreign function interface) es un mecanismo por el cual un programa escrito en un lenguaje de programación puede realizar llamadas a funciones o usar los servicios escritos en otro.1​ El término procede de la especificación de Common Lisp, aunque también se usa oficialmente en Haskell. Otros lenguajes usan otra terminología, así, el lenguaje Ada habla de "language bindings", mientras que en Java se denomina Java Native Interface, o JNI. No obstante, se suele hablar de forma genérica de Foreign function interface para referirse a estos mecanismos.

Por supuesto, a pesar de su nombre, las FFIs no se limitan a llamadas de función; muchos FFI permiten llamadas a métodos de objetos; e incluso algunos permiten el trasiego de tipos de datos no triviales y/u objetos entre distintos lenguajes.

El término foreign function interface no se suele usar para describir entornos de ejecución multilenguajes como Microsoft Common Language Runtime, donde existe un "sustrato" común que permite a cualquier lenguaje usar los servicios definidos por otro, siempre que cumplan el Common Language Specification. Además, existen muchas otras arquitecturas como Java RMI, RPC, CORBA y SOAP, que permiten usar servicios escritos en otros lenguajes; a tales arquitecturas no se les suele considerar FFIs.