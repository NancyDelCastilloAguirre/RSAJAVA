/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg08rsalibreria;

/**
 *
 * @author alumno
 */

import java.io.*;
import java.security.*;
import javax.crypto.*;
//bouncycastle sirve para manejar numeritos mas grandotes :D
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*La librería de BC nos sirve para el cálculo de los numeros p q n fi
aún mas grandotes, para la generación de llaves publicas y privadas
mas seguras
*/

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        
       /*Primero tenemos que añadir el nuevo proovedor de algoritmo debido a que security
       no tiene soporte para BC*/
       
       Security.addProvider(new BouncyCastleProvider());
       //vamos a la generacion de las claves
       
        System.out.println("1.- Crear las llaves publicas y privadas");
    
        //Para eso tenemos que usar la KeyPairGeneratos
        
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA", "BC"); //Instancia del algoritmo y el proveerodr
        
        //Inicializamos el tamaño de la llave
         
        keygen.initialize(512);
        
        //vamos a asignar la llave publica y privada
        KeyPair clavesRSA = keygen.generateKeyPair();
                
        //definimos a la llave publica
        PublicKey clavePublica = clavesRSA.getPublic();
        
        //definimos a la llave privada
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        
        System.out.println("2.- Introducir el texto plano que desea ingresar (máximo 64 caracteres)");
        
        //almacenar el texto en un arreglo de bytes
        
        byte[] bufferplano = leerLinea(System.in);
        
        //ciframos
        
        Cipher cifrado = Cipher.getInstance("RSA", "BC"); //Le decimos que es de RSA usando BouncyCastle
        
        //Ciframos con publica
        cifrado.init(Cipher.ENCRYPT_MODE, clavePublica);
        
        System.out.println("3.- Ciframos con la clave publica");
        
        byte[] buffercifrado = cifrado.doFinal(bufferplano);
        
        System.out.println("Texto cifrado: ");
        //no tiene formato el texto
        mostrarBytes(buffercifrado); 
        
        System.out.println(""); // salto de linea
        
        //Desciframos con clave privada
        cifrado.init(Cipher.DECRYPT_MODE, clavePrivada);
        
        System.out.println("4.- Desciframos con la la clave privada");
        
        byte[] bufferdescifrado = cifrado.doFinal(buffercifrado);
        
        System.out.println("Texto descifrado: ");
    
        mostrarBytes(bufferdescifrado);
        
        System.out.println("");
        System.out.println("");
         
        //Ahora lo hacemos pero al revés 
        
        cifrado.init(Cipher.ENCRYPT_MODE, clavePrivada);
        
        System.out.println("5.- Ciframos con la clave privada: ");
        
        buffercifrado = cifrado.doFinal(bufferplano);
        mostrarBytes(buffercifrado);
        
        System.out.println("");
        
        //desciframos con publica
        cifrado.init(Cipher.DECRYPT_MODE, clavePublica);
        
        System.out.println("6.- Desciframos con clave publica");
        
        bufferdescifrado = cifrado.doFinal(buffercifrado);
        
        System.out.println("Texto descifrado: ");
        
        mostrarBytes(bufferdescifrado);
        
        System.out.println("");
    }

    private static byte[] leerLinea(InputStream in) throws IOException {
        //definir como vamos a realizar la lectura del bloque
        byte[] buffer1 = new byte[1000];
        int i = 0;    
        byte c;
        
        c = (byte)in.read();
        
        while((c != '\n') && (i < 1000)){ //Mientras esos caracteres sean diferentes da el salto de linea
            buffer1[i] = c; //mi buffer1 es igual a los caracteres
            c = (byte)in.read();
            i++;
        }
        
        byte[] buffer2 = new byte[i];
        for (int j = 0; j < i; j++) {
            buffer2[j] = buffer1[j];
        }
        
        //con esto le damos la lectura y seguimiento al bloque de los caracteres que estamos pasando a bytes
        return buffer2;
    }

    
    
    private static void mostrarBytes(byte[] buffer) {
        //va a llamar a un buffer por que lo que queremos es que escriba lo que viene del buffer desde 0 hasta su length
        System.out.write(buffer, 0, buffer.length);
    }
    
}
