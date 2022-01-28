import React, { useState, useEffect } from 'react';

const AuthContext = React.createContext({
  user: {},
  isLoggedIn: false,
  login: (user) => {},
  logout: () => {},
});

const retrieveStoredUser = () => {
  const storedUser = localStorage.getItem('user');

  return {
    user: storedUser
  };
};

export const AuthContextProvider = (props) => {
  const storedUser = retrieveStoredUser();
  const initialUser = storedUser.user ? storedUser : undefined;
  const [user, setUser] = useState(initialUser);

  const userIsLoggedIn = !!user;

  const logoutHandler = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  const loginHandler = (user) => {
    setUser(user);
    localStorage.setItem('user', user);
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