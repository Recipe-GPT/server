package com.recipe.gpt.common.util;

import com.recipe.gpt.common.exception.FileException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileValidateUtils {

    public static void imageValidationCheck(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new FileException("이미지 파일이 비어있습니다.");
        }
        if (!isSupportedContentType(multipartFile.getContentType())) {
            throw new FileException("PNG, JPG만 허용됩니다.");
        }
    }

    private static boolean isSupportedContentType(String contentType) {
        return Objects.equals(contentType, "image/png")
            || Objects.equals(contentType, "image/jpg")
            || Objects.equals(contentType, "image/jpeg");
    }

}
