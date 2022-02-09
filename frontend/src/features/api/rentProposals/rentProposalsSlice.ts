import { BaseApiSlice } from '../baseApiSlice';
import {
	RentProposal,
	ListRentProposalsParameters,
	CreateRentProposalParameters,
	UpdateRentProposalParameters
} from './types';
import {Article} from "../articles/types";
import paginatedResponse, {PaginatedData} from "../paginatedResponse";

const RentProposalsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findProposal: build.query<RentProposal, URL>({
			query: (url) => url.toString()
		}),

		listProposals: build.query<PaginatedData<RentProposal[]>, ListRentProposalsParameters>({
			query: ({ userId, page, type, state }) => `proposals/${type}?user=${userId}&state=${state}${page != null ? `&page=${page}` : ''}`,
			transformResponse: (response: RentProposal[], meta
			) => paginatedResponse(response, meta)
		}),

		createProposal: build.mutation<RentProposal, CreateRentProposalParameters>({
			query: (args) => ({
				url: 'proposals',
				method: 'POST',
				body: args
			})
		}),

		updateProposal: build.mutation<void, UpdateRentProposalParameters>({
			query: ({ url, ...args }) => ({
				url: url.toString(),
				method: 'PUT',
				body: args
			})
		})
	})
});

export const {
	useListProposalsQuery: useListRentProposals,
	useFindProposalQuery: useFindRentProposal,
	useCreateProposalMutation: useCreateRentProposal,
	useUpdateProposalMutation: useUpdateRentProposal
} = RentProposalsApiSlice;
