from src.domain.users.enterprise.entities.company import Company
from src.domain.users.application.interfaces.icompanies_repository import ICompaniesRepository
from ..entities.company import CompanyTable
from ..mappers.company_mapper import SQLAlchemyCompanyMapper
from ..settings.connection import db_connection_handler


class CompaniesRepository(ICompaniesRepository):
    def __init__(self, db_connection: db_connection_handler):
        self.__db_connection = db_connection

    def create(self, company: Company):
        with self.__db_connection as database:
            company_data = SQLAlchemyCompanyMapper.to_sqlalchemy(company)
            company_sql = CompanyTable(**company_data)

            try:
                database.session.add(company_sql)
                database.session.commit()
            except Exception as e:
                return e

    def find_by_cnpj(self, cnpj: str) -> Company | None:
        with self.__db_connection as database:
            company_sql = database.session.query(CompanyTable).filter_by(cnpj=cnpj).first()
            if company_sql:
                return SQLAlchemyCompanyMapper.to_domain(company=company_sql)
            return None
