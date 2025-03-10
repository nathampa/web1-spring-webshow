package br.com.ifs.spring1.service;

import io.jsonwebtoken.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface IArquivoService {
    public String salvarArquivo(MultipartFile arquivo) throws IOException, java.io.IOException;
}
