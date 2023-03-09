let express = require('express');
let router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'The Matrix' });
});

router.get('/agent/', function (req, res, next) {
  res.render('agent', { title: 'Agent', agent: 'Smith' });
});

router.get('/architect/', function (req, res, next) {
  res.render('architect', { title: 'Architect' });
});

router.get('/programs/', function (req, res, next) {
  res.render('programs', {})
});

router.get('/oracle/', function (req, res, next) {
  res.render('oracle', { title: 'Oracle' });
});

router.get('/merovingian/', function (req, res, next) {
  res.render('merovingian', { title: 'Merovingian' });
});

module.exports = router;
