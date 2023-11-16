
FROM node:18.17.1 as builder

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

# Copy package.json and install dependencies
COPY package.json /app/package.json
RUN npm install

# Install Angular CLI globally
RUN npm install -g @angular/cli@16.2.0

# Copy the rest of the application files
COPY . /app

# Expose port 4200
EXPOSE 4200

# CMD to start the Angular development server
CMD ["ng", "serve", "--host", "0.0.0.0"]
