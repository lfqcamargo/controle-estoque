from src.domain.users.enterprise.entities.company import Company
from ..entities.company import CompanyTable
from ..mappers.company_mapper import SQLAlchemyCompanyMapper
from ..settings.connection import db_connection_handler

from src.domain.users.application.interfaces.Icompanies_repository import ICompaniesRepository


class CompaniesRepository(ICompaniesRepository):
    def __init__(self, db_connection: db_connection_handler):
        self.__db_connection = db_connection

    def create(self, company: Company):
        with self.__db_connection as database:
            print(company)
            company_data = SQLAlchemyCompanyMapper.to_sqlalchemy(company)
            company_sql = CompanyTable(**company_data)

            try:
                database.session.add(company_sql)
                database.session.commit()
            except Exception as e:
                print(e)
                return e