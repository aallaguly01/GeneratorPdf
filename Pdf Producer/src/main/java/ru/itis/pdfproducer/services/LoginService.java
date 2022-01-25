package ru.itis.pdfproducer.services;

import ru.itis.pdfproducer.dto.EmailPasswordDto;
import ru.itis.pdfproducer.dto.TokenDto;

public interface LoginService {
    TokenDto login(EmailPasswordDto emailPassword);
}
