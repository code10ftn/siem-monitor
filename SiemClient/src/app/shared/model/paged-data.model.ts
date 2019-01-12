export class PagedData<T> {

    content = new Array<T>();

    size: number;

    totalElements: number;

    totalPages: number;

    number: number;

    numberOfElements: number;

    first: boolean;

    last: boolean;

    sort;
}
