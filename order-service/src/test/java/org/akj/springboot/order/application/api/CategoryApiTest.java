package org.akj.springboot.order.application.api;

import org.akj.springboot.common.domain.Result;
import org.akj.springboot.order.domain.product.service.CategoryDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryApiTest {

	@Mock
	private CategoryDomainService categoryDomainService;

	@InjectMocks
	private CategoryApi categoryController;

	@Test
	public void testDeleteCategory() {
		// Arrange
		Long categoryId = 1L;

		// Act
		Result result = categoryController.deleteCategory(categoryId);

		// Assert
		verify(categoryDomainService).deleteById(categoryId);
		assertEquals(Result.successWithEmptyResponse(), result);
	}
}
