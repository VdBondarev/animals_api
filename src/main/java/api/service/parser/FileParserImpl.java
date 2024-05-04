package api.service.parser;

import static api.constant.NumbersConstantsHolder.ZERO;

import api.dto.AnimalCreateRequestDto;
import api.mapper.AnimalMapper;
import api.model.Animal;
import api.model.Sex;
import api.model.Type;
import api.service.strategy.AnimalCategoryStrategy;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FileParserImpl implements FileParser {
    private final AnimalMapper animalMapper;
    private final AnimalCategoryStrategy categoryStrategy;

    @Override
    public List<Animal> parse(List<AnimalCreateRequestDto> requestDtos) {
        List<Animal> animals = new ArrayList<>(requestDtos.size());
        for (AnimalCreateRequestDto requestDto : requestDtos) {
            if (isNotValid(requestDto)) {
                continue;
            }
            try {
                Type type = Type.fromString(requestDto.getType());
                Sex sex = Sex.fromString(requestDto.getSex());
                Long categoryId = categoryStrategy
                        .getAnimalCategoryService(requestDto.getCost())
                        .getCategory(requestDto.getCost());
                Animal animal = animalMapper.toModel(requestDto)
                        .setType(type)
                        .setSex(sex)
                        .setCategoryId(categoryId);
                animals.add(animal);
            } catch (Exception e) {
                /**
                 * skipping animals that caused exceptions
                 * for example while parsing type or sex
                 */
                continue;
            }
        }
        return animals;
    }

    private boolean isNotValid(AnimalCreateRequestDto dto) {
        return (dto.getName() == null || dto.getName().isEmpty())
                && dto.getCost() <= ZERO && dto.getWeight() <= ZERO;
    }
}