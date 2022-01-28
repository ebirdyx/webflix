import React, {useContext, useState} from "react";
import {Button, Card, Col, Modal} from "react-bootstrap";
import {Movie} from "../api/types";
import AuthContext from '../store/auth_context';
import MovieApi from "../api/MovieApi";

interface Props {
  movie: Movie;
}

const MovieCard : React.FC<Props> = ({movie}) => {
  const authContext = useContext(AuthContext);

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => {
    MovieApi()
      .rent(authContext.user.id, movie.id)
      .then(res => setShow(true));
  }

  return (
    <div id={movie.id.toString()}>
      <Col>
        <Card id={movie.id.toString()} style={{width: '15rem'}}>
          <Card.Img variant="top" src={movie.cover} />
          <Card.Body>
            <Card.Title>{movie.title}</Card.Title>
            <Card.Text>{movie.synopsis}</Card.Text>
            <Button variant="primary" onClick={handleShow}>Rent this title</Button>
          </Card.Body>
        </Card>
      </Col>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Movie rented</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          This movie: "{movie.title}" was successfully rented
          <br />
          To user: "{authContext.user.firstName}, {authContext.user.lastName}"
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default MovieCard;