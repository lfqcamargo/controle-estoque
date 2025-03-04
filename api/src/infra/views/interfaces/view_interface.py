from abc import ABC, abstractmethod
from src.infra.views.http_types.http_request import HttpRequest
from src.infra.views.http_types.http_response import HttpResponse


class ViewInterface(ABC):

    @abstractmethod
    def handle(self, http_request: HttpRequest) -> HttpResponse:
        pass
