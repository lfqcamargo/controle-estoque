from dataclasses import dataclass
from uuid import UUID
from datetime import datetime

@dataclass(frozen=False)
class CreateCompanyRequestDTO:
    id: UUID | None
    cnpj: str
    name: str
    created_at: datetime | None

