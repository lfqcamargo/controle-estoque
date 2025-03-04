import uuid

from sqlalchemy import Column, String, DateTime, UUID
from ..settings.base import Base
from sqlalchemy.sql import func


class CompanyTable(Base):
    __tablename__ = "companies"

    id = Column(UUID, primary_key=True, default=uuid.uuid4())
    cnpj = Column(String, nullable=False, unique=True)
    name = Column(String, nullable=False)
    created_at = Column(DateTime, nullable=False, default=func.now())

