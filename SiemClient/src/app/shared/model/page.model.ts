export class Page {

    size = 0;

    totalElements = 0;

    totalPages = 0;

    pageNumber = 0;

    setPagedData(pagedData): void {
        this.pageNumber = pagedData.number;
        this.size = pagedData.size;
        this.totalElements = pagedData.totalElements;
        this.totalPages = pagedData.totalPages;
    }
}
