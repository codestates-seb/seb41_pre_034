/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx}'],
  theme: {
    extend: {},
    backgroundColor: {
      focused: '#f1f1f3',
    },
    fontWeight: {
      focused: '700',
    },
    borderColor: {
      focused: '#f38226',
    },
    borderWidth: {
      focused: '3px',
    },
  },
  plugins: [require('@tailwindcss/line-clamp')],
};
