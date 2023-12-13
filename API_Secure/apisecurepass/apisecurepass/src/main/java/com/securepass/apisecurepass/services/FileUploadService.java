package com.securepass.apisecurepass.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileUploadService {
    // Diretório onde as imagens serão salvas
    private final Path diretorioImg = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img");

    // Método para realizar o upload de uma imagem
    public String FazerUpload(MultipartFile face) throws IOException {

        if (face.isEmpty()) {
            // Verifica se a imagem está vazia
            System.out.println("Imagem vazia");
            return null;
        }

        // Obtém o nome original da imagem e sua extensão
        String nomeOriginal = face.getOriginalFilename();
        String[] nomeArquivoArray = nomeOriginal.split("\\.");
        String extensaoArquivo = nomeArquivoArray[nomeArquivoArray.length - 1];

        // Gera um prefixo para o nome do arquivo baseado na data e hora atuais
        String prefixoArquivo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));

        // Define um novo nome para a imagem combinando o prefixo e a extensão do arquivo
        String novoNomeImagem = prefixoArquivo + "." + extensaoArquivo;

        // Cria um arquivo para a imagem no diretório especificado
        File imagemCriada = new File(diretorioImg + "\\" + novoNomeImagem);

        // Escreve os bytes da imagem no arquivo recém-criado
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(imagemCriada));
        stream.write(face.getBytes());
        stream.close();

        // Retorna o nome da imagem após o upload
        return novoNomeImagem;
    }
}
