import React from "react";
import {Button, Card, Col} from "react-bootstrap";
import {Movie} from "../api/types";

interface Props {
  movie: Movie;
}

const MovieCard : React.FC<Props> = ({movie}) => {
  return (
    <div id={movie.id.toString()}>
      <Col>
        <Card id={movie.id.toString()} style={{width: '15rem'}}>
          <Card.Img variant="top" src={movie.cover} />
          <Card.Body>
            <Card.Title>{movie.title}</Card.Title>
            <Card.Text>{movie.synopsis}</Card.Text>
            <Button variant="primary">Rent this title</Button>
          </Card.Body>
        </Card>
      </Col>
    </div>
  );
};

export default MovieCard;