package ir.maktabsharif.test_app.mapper;

import ir.maktabsharif.test_app.dto.question.OptionRequest;
import ir.maktabsharif.test_app.model.questions.Option;

import java.util.Optional;

public class OptionMapper {
    public static OptionRequest toOptionRequest(Option option){
        return OptionRequest.builder()
                .text(option.getText())
                .correct(option.isCorrect())
                .build();
    }
    public static Option toEntity(OptionRequest optionRequest){
        return Option.builder()
                .text(optionRequest.getText())
                .correct(optionRequest.isCorrect())
                .build();
    }
}
