import { test, expect } from '@playwright/test';

test.describe('Astropa Application', () => {
  test('should load the home page', async ({ page }) => {
    // Navigate to the home page
    await page.goto('/');

    // Wait for the page to load
    await page.waitForLoadState('domcontentloaded');

    // Check if the page title contains "Astropa"
    await expect(page).toHaveTitle(/Astropa/i);
  });

  test('should have navigation elements', async ({ page }) => {
    await page.goto('/');
    
    // Wait for the page to load
    await page.waitForLoadState('domcontentloaded');
    
    // Check for common navigation elements
    // You can update these selectors based on your actual app structure
    const body = page.locator('body');
    await expect(body).toBeVisible();
    
    // This is a basic smoke test - you can expand this based on your app's structure
    console.log('Basic smoke test passed - app is loading');
  });

  test('should be responsive', async ({ page }) => {
    // Test mobile viewport
    await page.setViewportSize({ width: 375, height: 667 });
    await page.goto('/');
    
    await page.waitForLoadState('domcontentloaded');
    
    // Basic check that the page still loads in mobile view
    const body = page.locator('body');
    await expect(body).toBeVisible();
    
    // Test desktop viewport
    await page.setViewportSize({ width: 1920, height: 1080 });
    await page.waitForLoadState('domcontentloaded');
    
    await expect(body).toBeVisible();
  });
});