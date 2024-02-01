package com.securepass.apisecurepass.config;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileInputStream;

@Service
public class Blob {


    // Nome do arquivo gerado
    static String CaminhoArquivo( String fotoGerada ){

        String caminhoFoto = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\" + fotoGerada;

        try{
            File verificarFoto = new File( caminhoFoto );

            if( verificarFoto.exists() || verificarFoto.isDirectory() )
            {
                return caminhoFoto;

            }else{
                return "";
            }
        }catch (Exception e) {
            return "";
        }

    }
    
    
    
    
    // String de comunicação com o Azure Blob Storage ( String de cadeia de conexão )
    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=securepass;AccountKey=xRFzevfEIqCJ30rilIwKqI6SeZvugSOSYP8uij9RXUu6c9tqeCx3yxpWCZ94/PD0esQgieuBKSqb+ASt/k2tJQ==;EndpointSuffix=core.windows.net";

    public static String UploadFileToBlob(String nomeArquivo) {
        try {
            // Conectando com a conta do Azure
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Capturando  a conexao com o BlobAzure
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Conectando com o container criado no blob storage
            CloudBlobContainer container = blobClient.getContainerReference("securepasscontainer");

            // Criando um novo arquivo dentro do container ( nomeArquivo = variavel com o nome do arquivo )
            CloudBlockBlob blob = container.getBlockBlobReference(nomeArquivo);

            // Capturar o caminho da foto gerada com uma função para pegar a raiz do caminho
            var caminhoFoto = CaminhoArquivo(nomeArquivo);

            // Criando uma instancia para receber o conteudo do arquivo gerado
            File conteudoFoto = new File(caminhoFoto);

            // Gerando uma copia do arquivo para ser enviado ao BlobStorage
            var copiaArquivo = new FileInputStream(conteudoFoto);

            // Mandando a foto para o blob storage
            blob.upload(copiaArquivo, conteudoFoto.length());

            return caminhoFoto;


        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }

        return "false";
    }
}

