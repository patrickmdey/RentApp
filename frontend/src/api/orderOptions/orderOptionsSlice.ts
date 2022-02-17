import { BaseApiSlice } from '../baseApiSlice';
import { OrderOption } from './types';

const OrderOptionsSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		listOrderOptions: build.query<OrderOption[], void>({
			query: () => 'orderOptions',
			providesTags: ['OrderOptions']
		})
	})
});

export const { useListOrderOptionsQuery: useListOrderOptions } = OrderOptionsSlice;
