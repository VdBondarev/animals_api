package api.service;

import api.dto.AnimalCreateRequestDto;
import api.dto.AnimalResponseDto;
import api.mapper.AnimalMapper;
import api.repository.AnimalRepository;
import api.service.parser.FileParser;
import api.service.reader.FileReader;
import api.service.strategy.AnimalReaderStrategy;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final AnimalReaderStrategy readerStrategy;
    private final FileParser fileParser;

    @Override
    public List<AnimalResponseDto> upload(MultipartFile file) throws FileUploadException {
        String contentType = file.getContentType();
        FileReader reader = readerStrategy.getReaderHandler(contentType);
        List<AnimalCreateRequestDto> requestDtos = reader.readFromFile(file);
        return new ArrayList<>();
    }
}
