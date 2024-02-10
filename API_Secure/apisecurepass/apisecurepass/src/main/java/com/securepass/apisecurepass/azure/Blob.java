package com.securepass.apisecurepass.azure;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;

@Service
public class Blob {

    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=securepass;AccountKey=xRFzevfEIqCJ30rilIwKqI6SeZvugSOSYP8uij9RXUu6c9tqeCx3yxpWCZ94/PD0esQgieuBKSqb+ASt/k2tJQ==;EndpointSuffix=core.windows.net";


    // Criando função para enviar arquivo
    public static String UploadFileToBlob(MultipartFile arquivo, String nomeArquivo) {
        try {
            // Acessando os recursos da conta por meio da connection string
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Acessando os dados de conexao com o blob
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Reconhecendo o container criado
            CloudBlobContainer container = blobClient.getContainerReference("securepasscontainer");

            // Criando uma referencia do novo arquivo que será gerado
            CloudBlockBlob blob = container.getBlockBlobReference(nomeArquivo);

            blob.upload(arquivo.getInputStream(), arquivo.getSize());

            return "Imagem enviada com sucesso";


        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }

        return "Imagem não enviada";
    }
}

