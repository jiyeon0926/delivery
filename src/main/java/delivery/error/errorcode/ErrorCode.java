package delivery.error.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    CATEGORY_STEP_OVER(BAD_REQUEST , "카테고리는 3단계 까지 가능합니다."),
    STORE_COUNT_OVER(BAD_REQUEST, "가게는 3개까지 등록할 수 있습니다."),
    NOT_REGISTER_STORE(BAD_REQUEST, "손님은 가게를 등록할 수 없습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_PASSWORD(UNAUTHORIZED, "password 가 일치하지 않습니다."),
    NOT_OWNER_CRUD(UNAUTHORIZED,"가게 운영자만 메뉴를 관리 할 수 있습니다."),



    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    SESSION_NOT_FOUND(NOT_FOUND, "로그인이 필요합니다."),
    ID_NOT_FOUND(NOT_FOUND, "id를 찾을 수 없습니다."),
    STORE_NOT_FOUND(NOT_FOUND, "등록된 가게를 찾을 수 없습니다."),
    MENU_NOT_FOUND(NOT_FOUND, "등록된 메뉴를 찾을 수 없습니다."),
    ALREADY_DELETE_MENU(NOT_FOUND, "삭제된 메뉴입니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    BANED_EMAIL(CONFLICT,"이미 탈퇴한 유저입니다");

    private final HttpStatus httpStatus;
    private final String detail;
}
