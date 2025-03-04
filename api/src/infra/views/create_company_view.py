from src.domain.users.dto.create_company_request_dto import CreateCompanyRequestDTO
from src.infra.controllers.create_company_controller import CreateCompanyController
from src.infra.validators.create_company_validator import create_company_validator
from src.infra.views.http_types.http_request import HttpRequest
from src.infra.views.http_types.http_response import HttpResponse
from src.infra.views.interfaces.view_interface import ViewInterface


class CreateCompanyView(ViewInterface):
    def __init__(self, controller: CreateCompanyController) -> None:
        self.__controller = controller

    def handle(self, http_request: HttpRequest) -> HttpResponse:
        create_company_validator(http_request)

        dto = CreateCompanyRequestDTO(
            id=None,
            cnpj=http_request.body.get("cnpj"),
            name=http_request.body.get("name"),
            created_at=None,
        )

        body_response = self.__controller.handle(dto)

        return HttpResponse(status_code=201, body=body_response)