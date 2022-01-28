import React, { useState, useEffect } from 'react';

const AuthContext = React.createContext({
  user: {
    id: 0,
    username: '',
    firstName: '',
    lastName: '',
  },
  isLoggedIn: false,
  login: (user) => {},
  logout: () => {},
});

const retrieveStoredUser = () => {
  let storedUser = localStorage.getItem('user');

  if (storedUser !== null)
    storedUser = JSON.parse(storedUser);

  return {
    user: storedUser
  };
};

export const AuthContextProvider = (props) => {
  const storedUser = retrieveStoredUser();
  const initialUser = storedUser.user ? storedUser.user : undefined;
  const [user, setUser] = useState(initialUser);

  const userIsLoggedIn = !!user;

  const logoutHandler = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  const loginHandler = (user) => {
    setUser(user);
    localStorage.setItem('user', JSON.stringify(user));
  };

  const contextValue = {
    user: user,
    isLoggedIn: userIsLoggedIn,
    login: loginHandler,
    logout: logoutHandler,
  };

  return (
    <AuthContext.Provider value={contextValue}>
      {props.children}
    </AuthContext.Provider>
  );
};

export default AuthContext;