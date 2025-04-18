package com.spirnt.mission.discodeit.swagger;

import com.spirnt.mission.discodeit.enity.BinaryContent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Binary Content", description = "첨부파일 API")
public interface BinaryContentApiDocs {

  // 단건 조회 및 다운로드
  @Operation(summary = "첨부 파일 조회", operationId = "find")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "첨부 파일 조회 성공",
          content = @Content(schema = @Schema(implementation = BinaryContent.class))),
      @ApiResponse(responseCode = "404", description = "첨부 파일을 찾을 수 없음",
          content = @Content(examples = @ExampleObject(value = "BinaryContent with id {binaryContentId} not found")))
  })
  ResponseEntity<BinaryContent> getFile(
      @Parameter(name = "binaryContentId", description = "조회할 첨부 파일 ID") @PathVariable UUID binaryContentId);

  // 다건 조회
  @Operation(summary = "여러 첨부 파일 조회", operationId = "findAllByIdIn")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "첨부 파일 목록 조회 성공",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = BinaryContent.class))))
  })
  ResponseEntity<List<BinaryContent>> getFiles(
      @Parameter(name = "binaryContentIds", description = "조회할 첨부 파일 ID 목록") @RequestParam List<UUID> binaryContentIds);
}
