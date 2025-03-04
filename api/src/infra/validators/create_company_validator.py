from pydantic import BaseModel, ValidationError, EmailStr, Field
from src.infra.views.http_types.http_request import HttpRequest


def create_company_validator(http_request: HttpRequest) -> None:

    class BodyData(BaseModel):
        """
        Validate Body Schema
        """

        cnpj: str = Field(..., min_length=14, max_length=14)
        name: str = Field(..., min_length=3, max_length=30)

    try:
        BodyData(**http_request.body)
    except ValidationError as error:
        raise error
