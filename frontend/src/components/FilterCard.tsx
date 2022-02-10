import {Button, Card, Form, InputGroup} from 'react-bootstrap';
import {strings} from '../i18n/i18n';
import {useForm} from 'react-hook-form';
import {useListCategories} from '../features/api/categories/categoriesSlice';
import {useListLocations} from '../features/api/locations/locationsSlice';
import {useListOrderOptions} from '../features/api/orderOptions/orderOptionsSlice';
import FormInput from './Forms/FormInput';
import FormSelect from './Forms/FormSelect';
import {Category} from '../features/api/categories/types';
import LoadingComponent from './LoadingComponent';
import ErrorComponent from './Errors/ErrorComponent';

interface FilterCardForm {
    name: string;
    category: number;
    orderBy: string;
    location: string;
    endPrice: number;
    initPrice: number;
}

const UNSELECTED_DEFAULT = {
    id: '',
    description: strings.collection.filter.everyCategory,
    name: strings.collection.filter.everyLocation
};

interface FilterCardProps {
    onSubmit: (data: FilterCardForm) => void;
}

function FilterCard(props: FilterCardProps) {
    const {register, handleSubmit} = useForm<FilterCardForm>();

    const {data: categories, isSuccess: categoriesIsSucc} = useListCategories();
    const {data: locations, isSuccess: locationsIsSucc} = useListLocations();
    const {data: orderOptions, isSuccess: orderIsSucc} = useListOrderOptions();

    return (
        <>{categoriesIsSucc && categories && locationsIsSucc && locations && orderIsSucc && orderOptions &&
            <Card className='card-style filters-card col-md-3 col-lg-3 col-12'>
                <Card.Header className=' d-flex align-items-center '>
                    <h4 className='color-rentapp-black col-9'>{strings.collection.filter.title}</h4>
                </Card.Header>

                <Form onSubmit={handleSubmit(props.onSubmit)}>
                    <Card.Body style={{padding: '0px'}}>
                        <FormInput register={register} type='text' label={strings.collection.filter.name}
                                   name='name'/>
                        <FormSelect
                            register={register}
                            name='category'
                            label={strings.collection.filter.category}
                            options={
                                categories
                                    ? [UNSELECTED_DEFAULT, ...categories].map(({id, description}) => [
                                        id.toString(),
                                        description
                                    ])
                                    : []
                            }
                        />
                        <FormSelect
                            register={register}
                            name='orderBy'
                            label={strings.collection.filter.orderBy}
                            options={orderOptions ? orderOptions.map(({id, description}) => [id, description]) : []}
                        />
                        <FormSelect
                            register={register}
                            name='location'
                            label={strings.collection.filter.location}
                            options={
                                locations ? [UNSELECTED_DEFAULT, ...locations].map(({id, name}) => [id, name]) : []
                            }
                        />
                        <FormInput
                            register={register}
                            type='number'
                            label={strings.collection.filter.minPrice}
                            name='initPrice'
                            prependIcon='$'
                        />
                        <FormInput
                            register={register}
                            type='number'
                            label={strings.collection.filter.maxPrice}
                            name='endPrice'
                            prependIcon='$'
                        />
                    </Card.Body>
                    <Button type='submit' className='w-100 mt-3'>
                        {strings.collection.filter.button}
                    </Button>
                </Form>
            </Card>}
        </>
    );
}

export default FilterCard;
