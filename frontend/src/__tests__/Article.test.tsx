import { fireEvent, screen, waitFor } from '@testing-library/react';
import { setupServer } from 'msw/node';
import { act } from 'react-dom/test-utils';
import renderFromRoute from './utils/renderFromRoute';
import userEvent from '@testing-library/user-event';
import { publishArticleHandlers } from './mocks/publishArticleHandlers';
import { LOGIN_MOCKED_DATA } from './mocks/moked-models';

setupServer(...publishArticleHandlers);

test('publish new article', async () => {
	await act(async () => sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN));

	await act(async () => renderFromRoute('/createArticle'));

	//Enter title
	fireEvent.change(screen.getByTestId('title'), {
		target: { value: 'Mock Article' }
	});

	//Enter descriptionn
	fireEvent.change(screen.getByTestId('description'), {
		target: { value: 'Mock Description' }
	});

	fireEvent.change(screen.getByTestId('pricePerDay'), {
		target: { value: 1 }
	});

	//Select category
	userEvent.click(await screen.getByTestId('1'));

	const file = new File([new ArrayBuffer(1)], 'file.jpg');
	fireEvent.change(screen.getByTestId('image'), {
		target: { files: [file] }
	});

	//Next step
	userEvent.click(screen.getByTestId('submit-button'));

	expect(await screen.findByText('Mock Article')).toBeInTheDocument();
});
