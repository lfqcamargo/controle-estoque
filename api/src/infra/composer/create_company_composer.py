from src.domain.users.application.services.create_company import CreateCompanyService
from src.infra.controllers.create_company_controller import CreateCompanyController
from src.infra.database.postgree.repositories.companies_repository import CompaniesRepository
from src.infra.database.postgree.settings.connection import db_connection_handler
from src.infra.views.create_company_view import CreateCompanyView


def create_company_composer():
    model = CompaniesRepository(db_connection=db_connection_handler)
    service = CreateCompanyService(model)
    controller = CreateCompanyController(service)
    view = CreateCompanyView(controller)

    return view