package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.service.IArquivoService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ArquivoServiceImpl implements IArquivoService {
    private final String uploadDir = "G:\\Meu Drive\\MATÉRIAS IFS\\PROGRAMACAO WEB I\\web1-spring-webshow\\uploads\\musicas\\";


    @Override
    public String salvarArquivo(MultipartFile arquivo) throws IOException, java.io.IOException {
        Files.createDirectories(Paths.get(uploadDir)); // Garante que a pasta existe

        // Nome do arquivo (para evitar sobrescrita)
        String fileName = System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        // Salva o arquivo no servidor
        Files.write(filePath, arquivo.getBytes());

        // Retorna o caminho salvo
        return filePath.toString();
    }
}
