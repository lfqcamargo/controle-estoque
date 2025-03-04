from src.domain.users.application.interfaces.Icompanies_repository import ICompaniesRepository
from src.domain.users.dto.create_company_request_dto import CreateCompanyRequestDTO
from src.domain.users.enterprise.entities.company import Company


class CreateCompanyService:
    def __init__(self, companies_repository: ICompaniesRepository) -> None:
        self.__companies_repository = companies_repository

    def execute(self, props: CreateCompanyRequestDTO):
        company = Company.create(props)

        self.__companies_repository.create(company)

        return None
