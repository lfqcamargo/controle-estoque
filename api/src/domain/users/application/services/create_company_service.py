from src.domain.users.application.interfaces.icompanies_repository import ICompaniesRepository
from src.domain.users.dto.create_company_request_dto import CreateCompanyRequestDTO
from src.domain.users.enterprise.entities.company import Company
from src.core.errors.already_exists_error import AlreadyExistsError

class CreateCompanyService:
    def __init__(self, companies_repository: ICompaniesRepository) -> None | AlreadyExistsError:
        self.__companies_repository = companies_repository

    def execute(self, props: CreateCompanyRequestDTO) -> None:
        company_cnpj = self.__companies_repository.find_by_cnpj(props.cnpj)

        if company_cnpj is not None:
            raise AlreadyExistsError("Cnpj already exists.", "cnpj")

        company = Company.create(props)

        self.__companies_repository.create(company)

        return None
