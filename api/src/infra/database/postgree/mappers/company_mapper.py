from src.domain.users.enterprise.entities.company import Company

class SQLAlchemyCompanyMapper:

    @staticmethod
    def to_sqlalchemy(company: Company):
        return {
            "id": company.id,
            "cnpj": company.cnpj,
            "name": company.name,
            "created_at": company.created_at
        }