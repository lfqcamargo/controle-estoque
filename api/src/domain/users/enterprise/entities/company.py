from datetime import datetime
from uuid import UUID, uuid4

from src.core.entities.entity import Entity
from src.domain.users.dto.create_company_request_dto import CreateCompanyRequestDTO


class Company(Entity):
    def __init__(self, id: UUID, cnpj: str, name: str, created_at: datetime):
        self.id = id
        self.cnpj = cnpj
        self.name = name
        self.created_at = created_at

    @staticmethod
    def create(props: CreateCompanyRequestDTO) -> 'Company':
        if props.id is None:
            props.id = uuid4()

        if props.created_at is None:
            props.created_at = datetime.now()

        company = Company(props.id, props.cnpj, props.name, props.created_at)

        return company

    def to_dict(self) -> dict:
        return {
            "id": str(self.id),
            "cnpj": self.cnpj,
            "name": self.name,
            "created_at": self.created_at
        }