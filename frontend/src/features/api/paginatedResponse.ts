import { FetchBaseQueryMeta } from '@reduxjs/toolkit/dist/query/fetchBaseQuery';
import parse from 'parse-link-header';

export interface PaginatedData<T> {
	data: T;
	pages: parse.Links | null;
}

export default function paginatedResponse<T>(data: T, meta: FetchBaseQueryMeta | undefined): PaginatedData<T> {
	const pages = parse(meta?.response?.headers.get('Link'));
	return { data: data, pages: pages };
}
