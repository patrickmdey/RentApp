import { PaginatedData } from '../api/paginatedResponse';
import parse from 'parse-link-header';
import { FetchBaseQueryError } from '@reduxjs/toolkit/dist/query/fetchBaseQuery';
import { SerializedError } from '@reduxjs/toolkit';

interface PaginatedResponse<T> {
	data?: T;
	pages: parse.Links | null;
	error?: FetchBaseQueryError | SerializedError;
	isSuccess: boolean;
	isLoading: boolean;
}

export default function usePaginatedResponse<T>(res: {
	data?: PaginatedData<T>;
	error?: FetchBaseQueryError | SerializedError;
	isSuccess: boolean;
	isError: boolean;
	isLoading: boolean;
}): PaginatedResponse<T> {
	return {
		...res,
		data: res?.data?.data,
		pages: res?.data?.pages || null
	};
}
