from src.domain.users.enterprise.entities.company import Company
from src.infra.database.postgree.entities.company import CompanyTable
from src.domain.users.dto.create_company_request_dto import CreateCompanyRequestDTO

class SQLAlchemyCompanyMapper:

    @staticmethod
    def to_sqlalchemy(company: Company) -> CompanyTable:
        return {
            "id": company.id,
            "cnpj": company.cnpj,
            "name": company.name,
            "created_at": company.created_at
        }

    @staticmethod
    def to_domain(company: CompanyTable) -> Company:
        company = CreateCompanyRequestDTO(
            id = company.id,
            cnpj = company.cnpj,
            name = company.name,
            created_at = company.created_at,
        )
        return Company.create(company)
