import { Row, Badge } from 'react-bootstrap';
import { GeoAltFill, PersonFill, Tag } from 'react-bootstrap-icons';
import { ListArticleParameters } from '../../api/articles/types';
import { useListCategories } from '../../api/categories/categoriesSlice';
import { strings } from '../../i18n/i18n';
import { Category } from '../../api/categories/types';
import { useState, useEffect } from 'react';

function Filters(props: { filters: ListArticleParameters }) {
	const { filters } = props;
	const { data: categories, isSuccess: categIsSuccess, error: categError } = useListCategories();

	const [filterCategory, setFilterCategory] = useState('');

	// useEffect(
	// 	() =>
	// 		setFilterCategory(
	// 			categIsSuccess &&
	// 				categories &&
	// 				categories.find((cat: Category) => cat.id === filters.category)?.description
	// 		),
	// 	[categIsSuccess, categories, filters]
	// );

	return (
		<>
			{categIsSuccess && categories && (
				<Row className='mb-2'>
					{filters.name && filters.name.length > 0 && (
						<div className='d-flex align-items-center'>
							<span className='me-2 align-middle'>
								{strings.collection.filterInfo.search + ' ' + filters.name}
							</span>
						</div>
					)}

					{(filters.category || filters.user) && (
						<div className='d-flex align-items-center'>
							<p>{strings.collection.filterInfo.filtering}</p>

							{filters.category && ( //TODO agregar una cruz para sacar cada uno
								<Badge className='bg-color-secondary mx-2 d-flex align-items-center justify-content-center'>
									<p className='lead'>
										<Tag className='me-1' />
									</p>
									<p className='me-1'>pruebinha</p>
									<a href='${removeCategoryUrl}' className='text-light'>
										X
									</a>
								</Badge>
							)}

							{filters.user && (
								<div className='badge bg-color-secondary mx-2 p-2'>
									<PersonFill />
									<p>{filters.user}</p>
									<a href='${removeUserUrl}' className='text-light'>
										X
									</a>
								</div>
							)}
						</div>
					)}

					{filters.location && (
						<div className='d-flex align-items-center'>
							<span className='me-2 align-middle'>
								{filters.location}
								<GeoAltFill />
							</span>
						</div>
					)}
				</Row>
			)}
		</>
	);
}

export default Filters;
