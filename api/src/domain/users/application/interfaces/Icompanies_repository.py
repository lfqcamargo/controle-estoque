from abc import ABC, abstractmethod

from src.domain.users.enterprise.entities.company import Company


class ICompaniesRepository(ABC):

    @abstractmethod
    def create(self, company: Company) -> None:
        pass

    @abstractmethod
    def find_by_cnpj(self, cnpj: str) -> Company | None:
        pass
