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

    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=codereconhecimento;AccountKey=taIOzH1zxOsWN8HfwOLZqnCl1EO9x53f0txAc6jhBDlLB2/tc2Rr23n9pscd+IuF14YLm+gZ/o+i+ASt0e1C7w==;EndpointSuffix=core.windows.net";


    // Criando função para enviar arquivo
    public static String UploadFileToBlob(MultipartFile arquivo, String nomeArquivo) {
        try {
            // Acessando os recursos da conta por meio da connection string
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Acessando os dados de conexao com o blob
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Reconhecendo o container criado
            CloudBlobContainer container = blobClient.getContainerReference("containerblobstorage");

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

