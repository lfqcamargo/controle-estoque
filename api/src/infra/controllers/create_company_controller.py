from src.domain.users.application.services.create_company import CreateCompanyService
from src.domain.users.dto.create_company_request_dto import CreateCompanyRequestDTO


class CreateCompanyController:
    def __init__(self, create_company_service: CreateCompanyService) -> None:
        self.__create_company_service = create_company_service

    def handle(self, props: CreateCompanyRequestDTO):
        result = self.__create_company_service.execute(props)

        return result